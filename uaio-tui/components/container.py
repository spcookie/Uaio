from textual.layouts.horizontal import HorizontalLayout
from textual.app import ComposeResult
from textual.widget import Widget
from textual.widgets import Static, ContentSwitcher

from components.sidebar import SidebarItem, Sidebar


class Container(Static):

    def __init__(self, menus: (list[SidebarItem], list[Widget])):
        super().__init__()
        if not menus or len(menus) == 0:
            raise ValueError("menus is empty")
        self.menus = menus

    def compose(self) -> ComposeResult:
        with HorizontalLayout():
            item, widget = self.menus
            yield Sidebar([key for key in self.menus])
            with ContentSwitcher(initial="dashboard"):
                yield
