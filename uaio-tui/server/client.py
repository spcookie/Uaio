import httpx
from typing import Any, Dict, Optional

class Client:
    def __init__(self, base_url: str = "http://localhost:8080/api"):
        self.base_url = base_url
        self.client = httpx.AsyncClient(timeout=30.0)

    async def get(self, endpoint: str, params: Optional[Dict[str, Any]] = None) -> Dict[str, Any]:
        try:
            response = await self.client.get(f"{self.base_url}/{endpoint}", params=params)
            response.raise_for_status()
            return response.json()
        except httpx.HTTPError as e:
            return {"error": str(e)}

    async def post(self, endpoint: str, data: Dict[str, Any]) -> Dict[str, Any]:
        try:
            response = await self.client.post(f"{self.base_url}/{endpoint}", json=data)
            response.raise_for_status()
            return response.json()
        except httpx.HTTPError as e:
            return {"error": str(e)}

    async def close(self):
        await self.client.aclose()