# README

## 目錄

- [專案簡介](#專案簡介)
- [功能特性](#功能特性)
- [使用技術](#使用技術)
- [系統需求](#系統需求)
- [資料來源](#資料來源)
- [專案架構](#專案架構)
- [安裝與使用](#安裝與使用)
- [預覽](#預覽)
- [未來優化方向](#未來優化方向)
- [版權與聲明](#版權與聲明)

---

## 專案簡介

本專案旨在建立一個以 **Android Kotlin** 為基礎的應用程式，用於呈現臺北市立動物園的館區與動物資訊。透過 **MVVM 架構** 的實現，結合 **Jetpack Compose** 提供的高效 UI 設計功能，本專案同時支持資料的本地存儲與網路請求處理。

---

## 功能特性

1. **館區清單顯示**：
   - 展示館區名稱、描述及相關圖片，提供直觀的數據檢索。
2. **動物詳情檢視**：
   - 根據選取館區，顯示館區內動物的詳細資訊，包括其特徵和生活習性。
3. **本地數據支持**：
   - 實現 Room 資料庫集成，確保在離線環境下依然可存取資料。
4. **高效圖片加載**：
    - 使用 Coil 圖片處理框架實現流暢的圖片加載體驗。
5. **其他功能**：
    - BottomSheet的使用。
    - 圖片輪播。

---

## 使用技術

- **程式語言**：Kotlin
- **UI 框架**：Jetpack Compose
- **導航管理**：Android Navigation Component
- **設計模式**：MVVM（Model-View-ViewModel）
- **依賴注入**：Hilt
- **網路通信**：Retrofit + Gson
- **非同步處理**：Kotlin Coroutines
- **圖片處理**：Coil
- **資料庫管理**：Room

---

## 系統需求

- **最低支援 Android 版本**：API 24 (Android 7.0)

---

## 資料來源

本專案數據來源自臺北市政府開放資料平台，具體如下：

1. **臺北市立動物園 - 館區簡介**：
    - [館區簡介 API](https://data.taipei/api/v1/dataset/9683ba26-109e-4cb8-8f3d-03d1b349db9f?scope=resourceAquire)

2. **臺北市立動物園 - 動物資料**：
    - [動物資料 API](https://data.taipei/api/v1/dataset/6afa114d-38a2-4e3c-9cfd-29d3bd26b65b?scope=resourceAquire)

---

## 專案架構

```
app/
├── data/                       // 資料層
│   ├── local/                  // 本地資料管理
│   │   ├── db/                 // Room 資料庫配置
│   │   ├── entities/           // 資料表結構定義
│   ├── remote/                 // 網路請求模組
│   │   ├── response/           // API 響應模型
│   ├── repository/             // 資料處理與整合
├── domain/                     // 業務邏輯層
│   ├── usecase/                // UseCase 操作邏輯
├── ui/                         // 用戶界面層
│   ├── components/             // 元件設計
│   ├── navigation/             // 導航設計
│   ├── screen/                // 畫面設計
│   ├── viewmodel/             // ViewModel 管理
│   ├── theme/                 // UI 主題設計
├── di/                         // 依賴注入模組
├── config/                      // 應用程式配置
└── utils/                      // 通用工具類與輔助函數
```

---

## 安裝與使用

### **環境準備**

1. 安裝最新版本的 Android Studio。
2. 確保安裝 JDK 11 或以上版本。

### **專案克隆與運行**

1. 透過 Git 克隆專案：
   ```bash
   git clone http://192.168.77.234/bryantchi/zooappcompose.git
   cd <PROJECT_DIRECTORY>
   ```

2. 打開專案：
    - 使用 Android Studio 打開專案目錄。
    - 同步 Gradle 設定。

3. 運行應用：
    - 點擊執行按鈕，將應用部署到模擬器或實體設備。

---

## 預覽

以下是應用的 GIF 演示：

![應用演示](./demo.gif)

---

## 未來優化方向

1. 增強 Edge-to-Edge 視覺效果，提升用戶沉浸感。
2. 添加更多自動化 UI 測試，以提高應用的穩定性與可維護性。
3. 支援多語系切換，滿足更多用戶需求。

---

## 版權與聲明

本專案所使用之所有數據來自臺北市政府開放資料平台，僅用於學術研究與非商業用途。若有任何侵權疑慮，請即刻聯繫相關開發者進行刪除。

