from api_client import APIClient
from typing import Dict

class MainViewModel:
    def __init__(self):
        self.api_client = APIClient()
        self.features: Dict[str, str] = {
            "mock": "Mock管理",
            "dashboard": "仪表盘",
            "settings": "设置",
        }
        self.current_feature = None

    def get_features(self) -> Dict[str, str]:
        return self.features

    def set_current_feature(self, feature_id: str) -> None:
        self.current_feature = feature_id

    def get_current_feature(self) -> str:
        return self.current_feature

    async def close(self) -> None:
        await self.api_client.close()