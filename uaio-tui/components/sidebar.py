from textual.app import ComposeResult
from textual.widgets import Button, Static, ListView, Label, ListItem

class SidebarItem:

    def __init__(self, label: str, feature_id: str):
        self.label = label
        self.feature_id = feature_id


class Sidebar(Static):

    def __init__(self, items: list[SidebarItem]):
        super().__init__()
        self.items = items

    def compose(self) -> ComposeResult:
       with ListView():
            for item in self.items:
                yield ListItem(Label(item.label))