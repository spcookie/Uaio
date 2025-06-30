from textual.app import App, ComposeResult
from textual.binding import Binding
from textual.widgets import Footer, Static

from components.container import IContainer
from components.sidebar import SidebarItem
from pages.dashboard import Dashboard
from pages.mock import Mock


class UaoiTUI(App):
    TITLE = "A Question App"

    CSS_PATH = "style.tcss"

    BINDINGS = [
        Binding("^q", "quit", "quit", show=True),
    ]

    def __init__(self):
        super().__init__()

    def compose(self) -> ComposeResult:
        # yield Header(show_clock=True)
        yield IContainer(
            items=[
                SidebarItem("Dashboard", "dashboard"),
                SidebarItem("Mock", "mock"),
                SidebarItem("Util", "util"),
                SidebarItem("Setting", "setting")
            ],
            widgets=[
                Dashboard(feature_id="dashboard"),
                Mock(feature_id="mock"),
                Static("Util", id="util"),
                Static("Setting", id="setting"),
            ],
            sidebar_title="Menu"
        )
        yield Footer()

    def on_mount(self) -> None:
        self.theme = "tokyo-night"


if __name__ == "__main__":
    app = UaoiTUI()
    app.run()
