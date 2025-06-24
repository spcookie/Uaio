from textual.widgets import Static, LoadingIndicator
from textual.app import ComposeResult
from textual.message import Message
from api_client import APIClient

class FeatureContent(Static):
    class DataLoaded(Message):
        def __init__(self, data: dict) -> None:
            self.data = data
            super().__init__()

    def __init__(self, feature_id: str, api_client: APIClient):
        super().__init__()
        self.feature_id = feature_id
        self.api_client = api_client
        self.styles.padding = (1, 2)

    def compose(self) -> ComposeResult:
        yield LoadingIndicator()
        yield Static("加载中...", id="content-text")

    async def on_mount(self) -> None:
        try:
            data = await self.api_client.get(self.feature_id)
            self.post_message(self.DataLoaded(data))
        except Exception as e:
            self.query_one("#content-text").update(f"加载失败: {str(e)}")

    def on_feature_content_data_loaded(self, message: DataLoaded) -> None:
        self.query_one(LoadingIndicator).remove()
        content = message.data.get("content", "无数据") if not message.data.get("error") else f"错误: {message.data['error']}"
        self.query_one("#content-text").update(content)