from typing import Dict, List, Optional
from dataclasses import dataclass

@dataclass
class MockRequest:
    id: str
    name: str
    method: str
    url: str
    path_params: Dict[str, str]
    query_params: Dict[str, str]
    response_headers: Dict[str, str]
    response_body: Optional[str]

class MockViewModel:
    def __init__(self):
        self.mock_requests: Dict[str, MockRequest] = {}
        self.selected_request: Optional[str] = None

    def add_request(self, request: MockRequest) -> None:
        self.mock_requests[request.id] = request

    def update_request(self, request: MockRequest) -> None:
        if request.id in self.mock_requests:
            self.mock_requests[request.id] = request

    def delete_request(self, request_id: str) -> None:
        if request_id in self.mock_requests:
            del self.mock_requests[request_id]

    def get_request(self, request_id: str) -> Optional[MockRequest]:
        return self.mock_requests.get(request_id)

    def get_all_requests(self) -> List[MockRequest]:
        return list(self.mock_requests.values())

    def select_request(self, request_id: str) -> None:
        self.selected_request = request_id

    def get_selected_request(self) -> Optional[MockRequest]:
        if self.selected_request:
            return self.mock_requests.get(self.selected_request)
        return None