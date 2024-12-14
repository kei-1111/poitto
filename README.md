# P2HACKS2024 アピールシート 

### プロダクト名  
Poitto (ポイット)

### コンセプト  
日々の出来事や感情を記録することができ、毎日の感情の高まりを鎮めてくれるアプリ

### 対象ユーザ  
- ネガティブな感情を溜め込んでしまう人
- 日常の感情の高まりを吐き出したい人
- SNSや人前で本音を言いづらい人


### 利用の流れ  
日々のちょっとしたいらいらを発散したいとき
- **見る**
  - 自分と他人の投稿を見ていらいらを発散

- **捨てる**
  - いらいらを捨てるアニメーションでスッキリ
  - 天の声で慰めてくれる

### 推しポイント  
- 自分の投稿を捨てるアニメーションや、温かい言葉の返信により、ユーザの気持ちを落ち着かせるようにした
- 他人の投稿を見ることができ、共感したり、ストレス発散したりできるようにした

### スクリーンショット(任意)  

## 開発体制  

### 役割分担  
- 犬飼啓太郎：Android
- 中北竜馬：アイデア出し・補助・応援
- 木村星凱：バックエンド・LLM

### 開発における工夫した点  

#### コミュニケーション
できるだけ対面で話し合いを行い、認識のズレが起きないように意識した。  
また、ドキュメントを作成しあとから見返せるようにした。

#### Git/GitHub
**ブランチ戦略**  
今回は短期間のハッカソンということもあり、「GitHub Flow」を用いた。

**ブランチ名**  
新機能追加・既存機能強化の際には`featture/#IssueNumber`、バグ修正の際には`fix/#IssueNumber`を用いて何をするためのブランチなのかわかりやすいようにした。

**コミットメッセージ**  
Prefixをつけてどのようなコミットなのかわかりやすいようにした。  
以下は、Prefix一覧とどのようなときに使うのかの説明
 - fix: バグの改善
 - feat: 新機能作成やすでにある機能の改善
 - refactor: コードの改善
 - ci/cd: ci/cd周りの設定
 - docs: ドキュメントの更新

## 開発技術 

### 利用したプログラミング言語  

#### Android
- Kotlin

#### バックエンド
- Python

### 利用したフレームワーク・ライブラリ  

#### Android
- Jetpack Compose
- Material3
- Sceneview
- Firebase Authentication
- Cloud Firestore
- Cloud Storage for Firebase
- [その他ライブラリ](https://github.com/p2hacks2024/post-05/blob/main/gradle/libs.versions.toml)


#### バックエンド
- FastAPI
- Transformers (Hugging Face)
- Generative AI

### その他開発に使用したツール・サービス

**アイデア出し**：[FigJam](https://www.figma.com/board/8sqht8ywHSTNrc7ymZUJdX/P2hacks2024-%E3%82%A2%E3%82%A4%E3%83%87%E3%82%A2%E5%87%BA%E3%81%97?node-id=0-1&t=4cb0YZj2iVPY69an-1) 

**UIイメージ作成**：[Figma](https://www.figma.com/design/sKRte5zoJtD1cboe6lVBer/P2hacks2024-%E3%83%87%E3%82%B6%E3%82%A4%E3%83%B3?node-id=157-877&t=FnjdCnO7swJKO4Q5-1)

**3Dモデル作成**：Blender

**バージョン管理ツール**：Git/GitHub

**Android IDE**：Android Studio

**バックエンド IDE**：VSCode

**デプロイ**：Render

### バックエンド リポジトリ
https://github.com/BokunoLab-p2hacks/flash-backend/tree/main
