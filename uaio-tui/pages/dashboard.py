from textual.app import ComposeResult
from textual.widgets import Static, Markdown

MARKDOWN_CONTENT = """
# Utils all in one

This is a commonly used development tool aggregation.

- **choose a tool**
- **start**
"""


class Dashboard(Static):
    """Dashboard widget"""

    DEFAULT_CSS = """
    Dashboard {
        align: center middle;
        height: 1fr;
        text-style: bold;
    }
    .dashboard-container {
        border: double $primary-lighten-2;
        width: 50%;
    }
    """

    def __init__(self, feature_id: str):
        super().__init__()
        self.id = feature_id

    def compose(self) -> ComposeResult:
        with Static(classes="dashboard-container") as container:
            container.border_title = "[i]Uaio[/i]"
            yield Markdown(MARKDOWN_CONTENT)
