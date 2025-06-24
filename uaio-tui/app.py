from textual.app import App, ComposeResult
from textual.containers import Container, Horizontal
from textual.widgets import Header, Footer, Static
from textual.binding import Binding

from components.sidebar import SidebarButton
from components.feature import FeatureContent
from components.mock import MockFeature
from viewmodels.main_vm import MainViewModel

class UaoiTUI(App):
    CSS_PATH = "style.tcss"

    BINDINGS = [
        Binding("q", "quit", "退出", show=True),
    ]

    def __init__(self):
        super().__init__()
        self.view_model = MainViewModel()

    def compose(self) -> ComposeResult:
        yield Header()
        with Horizontal():
            with Container(id="sidebar"):
                for feature_id, label in self.view_model.get_features().items():
                    yield SidebarButton(label, feature_id=feature_id)
            with Container(id="content"):
                yield Static("请选择一个功能", classes="welcome-text")
        yield Footer()

    def on_button_pressed(self, event: SidebarButton.Pressed) -> None:
        if isinstance(event.button, SidebarButton):
            if self.view_model.get_current_feature():
                self.query_one("#content").remove_children()

            if event.button.feature_id == "mock":
                self.query_one("#content").mount(MockFeature())
            else:
                feature_content = FeatureContent(event.button.feature_id, self.view_model.api_client)
                self.query_one("#content").mount(feature_content)

            self.view_model.set_current_feature(event.button.feature_id)

    async def on_unmount(self) -> None:
        await self.view_model.close()

if __name__ == "__main__":
    app = UaoiTUI()
    app.run()