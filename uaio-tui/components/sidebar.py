from textual.widgets import Button

class SidebarButton(Button):
    def __init__(self, label: str, feature_id: str):
        super().__init__(label)
        self.feature_id = feature_id