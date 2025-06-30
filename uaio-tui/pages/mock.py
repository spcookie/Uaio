from json import JSONDecoder

from textual.app import ComposeResult
from textual.widgets import Static, Tree


class Mock(Static):

    DEFAULT_CSS = """
    Mock {
        layout: grid;
        grid-size: 2;
        grid-columns: 1fr 5fr;
        height: 1fr;
    }
    """

    def __init__(self, feature_id: str):
        super().__init__()
        self.id = feature_id

    def compose(self) -> ComposeResult:
        mocks_tree: Tree[str] = Tree("mocks")

        mocks_tree.root.add("mock1", "mock1")

        yield mocks_tree
