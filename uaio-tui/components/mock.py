from textual.app import ComposeResult
from textual.containers import Horizontal, Vertical, Grid
from textual.widgets import Tree, Button, Input, Label, Tabs, Tab, TextArea
from textual.widgets.tree import TreeNode
from textual.message import Message
from textual.binding import Binding

from viewmodels.mock_vm import MockViewModel, MockRequest
from uuid import uuid4

class MockRequestTree(Tree):
    def __init__(self, view_model: MockViewModel):
        super().__init__("Mock请求列表")
        self.view_model = view_model

    def compose(self) -> ComposeResult:
        yield Button("新增请求", id="add_request")

    def on_mount(self) -> None:
        self.refresh_tree()

    def refresh_tree(self) -> None:
        self.root.remove_children()
        for request in self.view_model.get_all_requests():
            node = self.root.add(request.name, data=request.id)

    def on_tree_node_selected(self, event: Tree.NodeSelected) -> None:
        if event.node.data:
            self.view_model.select_request(event.node.data)
            self.app.query_one(RequestConfig).refresh_config()

    def on_button_pressed(self, event: Button.Pressed) -> None:
        if event.button.id == "add_request":
            new_request = MockRequest(
                id=str(uuid4()),
                name="新请求",
                method="GET",
                url="/api/path",
                path_params={},
                query_params={},
                response_headers={},
                response_body=""
            )
            self.view_model.add_request(new_request)
            self.refresh_tree()

class RequestConfig(Grid):
    def __init__(self, view_model: MockViewModel):
        super().__init__()
        self.view_model = view_model

    def compose(self) -> ComposeResult:
        with Horizontal():
            yield Input(placeholder="GET", id="method", classes="method")
            yield Input(placeholder="/api/path", id="url", classes="url")

        with Tabs():
            yield Tab("请求参数", id="params_tab")
            yield Tab("响应头", id="headers_tab")

        with Vertical(id="params_content"):
            with Horizontal():
                yield Label("Path参数")
                yield Button("+", id="add_path_param")
            with Vertical(id="path_params"):
                pass
            with Horizontal():
                yield Label("Query参数")
                yield Button("+", id="add_query_param")
            with Vertical(id="query_params"):
                pass

        with Vertical(id="headers_content", classes="hidden"):
            with Horizontal():
                yield Label("响应头")
                yield Button("+", id="add_header")
            with Vertical(id="response_headers"):
                pass

        with Vertical():
            yield Label("响应体")
            yield TextArea(id="response_body")

    def on_mount(self) -> None:
        self.refresh_config()

    def on_tabs_tab_activated(self, event: Tabs.TabActivated) -> None:
        if event.tab.id == "params_tab":
            self.query_one("#params_content").remove_class("hidden")
            self.query_one("#headers_content").add_class("hidden")
        elif event.tab.id == "headers_tab":
            self.query_one("#params_content").add_class("hidden")
            self.query_one("#headers_content").remove_class("hidden")

    def refresh_config(self) -> None:
        request = self.view_model.get_selected_request()
        if request:
            self.query_one("#method").value = request.method
            self.query_one("#url").value = request.url
            # 更新参数和响应配置
            self.update_params("path_params", request.path_params)
            self.update_params("query_params", request.query_params)
            self.update_params("response_headers", request.response_headers)
            if request.response_body:
                self.query_one("#response_body").text = request.response_body

    def update_params(self, container_id: str, params: dict) -> None:
        container = self.query_one(f"#{container_id}")
        container.remove_children()
        for key, value in params.items():
            with container:
                with Horizontal():
                    yield Input(key, classes="param-key")
                    yield Input(value, classes="param-value")
                    yield Button("×", classes="remove-param")

    def on_button_pressed(self, event: Button.Pressed) -> None:
        if event.button.id == "add_path_param":
            with self.query_one("#path_params"):
                with Horizontal():
                    yield Input("", classes="param-key")
                    yield Input("", classes="param-value")
                    yield Button("×", classes="remove-param")
        elif event.button.id == "add_query_param":
            with self.query_one("#query_params"):
                with Horizontal():
                    yield Input("", classes="param-key")
                    yield Input("", classes="param-value")
                    yield Button("×", classes="remove-param")
        elif event.button.id == "add_header":
            with self.query_one("#response_headers"):
                with Horizontal():
                    yield Input("", classes="param-key")
                    yield Input("", classes="param-value")
                    yield Button("×", classes="remove-param")
        elif event.button.classes == {"remove-param"}:
            event.button.parent.remove()

    def on_input_changed(self, event: Input.Changed) -> None:
        request = self.view_model.get_selected_request()
        if not request:
            return

        if event.input.id == "method":
            request.method = event.value
        elif event.input.id == "url":
            request.url = event.value
        elif event.input.classes == {"param-key"}:
            container = event.input.parent.parent
            params = {}
            for param in container.query(Horizontal):
                key = param.query_one(".param-key").value
                value = param.query_one(".param-value").value
                if key:
                    params[key] = value
            
            if container.id == "path_params":
                request.path_params = params
            elif container.id == "query_params":
                request.query_params = params
            elif container.id == "response_headers":
                request.response_headers = params

    def on_text_area_changed(self, event: TextArea.Changed) -> None:
        request = self.view_model.get_selected_request()
        if request and event.text_area.id == "response_body":
            request.response_body = event.value

class MockFeature(Horizontal):
    def __init__(self):
        super().__init__()
        self.view_model = MockViewModel()

    def compose(self) -> ComposeResult:
        yield MockRequestTree(self.view_model)
        yield RequestConfig(self.view_model)