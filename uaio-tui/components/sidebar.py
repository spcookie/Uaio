from textual import on
from textual.app import ComposeResult
from textual.content import Content
from textual.message import Message
from textual.widgets import Static, ListView, Label, ListItem


class SidebarItem:

    def __init__(self, label: str, feature_id: str):
        self.label = label
        self.feature_id = feature_id


class Sidebar(Static):
    """A sidebar widget."""

    DEFAULT_CSS = """
    ListView {
        border: panel $primary-darken-3;
        border-title-align: center
    }
    Label {
        padding: 1 2;
    }
    """

    class Changed(Message):
        """Sent when the selected feature changes."""

        def __init__(self, source: str, target: str):
            super().__init__()
            self.source = source
            self.target = target

    current: str | None = None

    def __init__(self, items: list[SidebarItem], title: str = ""):
        super().__init__()
        self.title = title
        self.items = items

    def compose(self) -> ComposeResult:
        with ListView() as list_view:
            list_view.border_title = self.title
            for item in self.items:
                yield ListItem(Label(Content.from_markup("[b]$content[b]", content=item.label)), id=item.feature_id)

    def on_mount(self) -> None:
        self.current = self.items[0].feature_id

    @on(ListView.Selected)
    def change(self, event: ListView.Selected):
        self.post_message(Sidebar.Changed(self.current, event.item.id))
        self.current = event.item.id
