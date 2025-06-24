# Uaoi-TUI

一个基于Textual的终端用户界面应用，通过RESTful API与Java后端服务交互。

## 功能特点

- 美观的终端用户界面
- 可扩展的功能列表
- 异步数据加载
- 与Java后端RESTful API集成

## 安装要求

- Python 3.7+
- pip（Python包管理器）

## 安装步骤

1. 克隆仓库：

```bash
git clone [repository-url]
cd Uaoi-TUI
```

2. 安装依赖：

```bash
pip install -r requirements.txt
```

## 运行应用

```bash
python app.py
```

## 使用说明

- 使用方向键或鼠标选择左侧功能列表中的功能
- 按 'q' 键退出应用
- 功能内容区域会显示从后端API获取的数据

## 后端API要求

后端API需要实现以下端点：

- GET /api/dashboard - 获取仪表盘数据
- GET /api/settings - 获取设置数据

响应格式示例：

```json
{
    "content": "显示内容"
}
```

## 开发说明

- `app.py` - 主应用文件
- `api_client.py` - API客户端
- 新功能可以通过扩展 `self.features` 字典来添加