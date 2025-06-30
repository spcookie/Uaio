from textual import on
from textual.app import ComposeResult
from textual.widget import Widget
from textual.widgets import Static, ContentSwitcher

from components.sidebar import SidebarItem, Sidebar


class IContainer(Static):
    """Container for the main content of the app."""

    DEFAULT_CSS = """
    IContainer {
        layout: grid;
        grid-size: 2;
        grid-columns: 1fr 5fr;
        height: 100%;
    }
    ContentSwitcher {
        padding: 1 1;
        height: 100vh;
    }
    """

    def __init__(self, items: list[SidebarItem], widgets: list[Widget], sidebar_title: str = ""):
        super().__init__()
        if not items or len(items) == 0:
            raise ValueError("items is empty")
        if not widgets or len(widgets) == 0:
            raise ValueError("widgets is empty")
        self.items = items
        self.widgets = widgets
        self.sidebar_title = sidebar_title

    def compose(self) -> ComposeResult:
        yield Sidebar(self.items, self.sidebar_title)
        init = self.items[0]
        with ContentSwitcher(initial=init.feature_id):
            yield from self.widgets

    @on(Sidebar.Changed)
    def change(self, event: Sidebar.Changed):
        self.query_one(ContentSwitcher).current = event.target
