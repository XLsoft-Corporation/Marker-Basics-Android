# Kudan Tutorials - Marker Basics

[English README is here](./README_en.md)

このチュートリアルでは、KudanAR を使い始めるための基本を説明します。以前のチュートリアルでは、フレームワークを Android プロジェクトに統合する方法について説明しました。プロジェクトに ARActivity を設定していない場合は、先に進む前に以前のチュートリアルをチェックすることをお勧めします。

このチュートリアルでは、バンドルされたアセットを利用するため、正しいアセットがプロジェクトにインポートされていることを確認してください。

このサンプルでは以下を利用します。

* Marker: spaceMarker.jpg
* Image: Augmentation: eyebrow.png
* Video: Augmentation: waves.mp4
* Alpha video augmentation: kaboom.mp4
* Model: bloodhoud.armodel / bloodhound.jet
* Model Texture: bloodhound.png

全てのアセットは[こちら](https://jp.xlsoft.com/demo2/kudan/tutorials/assets.zip)からダウンロード可能です。

注意: Android 6.0 以降では、正しい Permission を要求したことを確認します。そうしないと、アプリケーションがクラッシュします。


## Image Trackable の設定

Image Trackable を作成するには、まず追跡する画像が必要になります。iOS ネイティブでサポートされていれば、使用する画像のフォーマットについて制限はありません。良いマーカーとは何か？についての情報は以下のブログ記事を参照してください。

[良いマーカーとは? – The Kudan Developer Hub](https://www.xlsoft.com/doc/kudan/ja/what-makes-a-good-marker_jp/)

Note: 良くないマーカーでは、AR がけいれんまたは揺れているように見える問題が発生します。

```java
// Initialise image trackable
trackable = new ARImageTrackable("StarWars");
trackable.loadFromAsset("spaceMarker.jpg");

// Get instance of image tracker manager
ARImageTracker trackableManager = ARImageTracker.getInstance();

// Add image trackable to image tracker manager
trackableManager.addTrackable(trackable);
```

## Adding content to an Image Trackable

Image Trackable にコンテンツを追加するには、コンテンツを対応する ARNode に変換して Trackable の World（マーカーを囲む 3D 空間）に追加する必要があります。Kudan には 4つの異なる ARNode サブクラスがあります。

* ARImageNode
* ARVideoNode
* ARAlphaVideoNode
* ARModelNode

Note: アプリケーションに AR コンテンツを追加するときは、バックグラウンドスレッドに追加することを検討してください。これにより、カメラフィードの失速を防ぐことができます。

### Image Nodes

画像は ARImageNode クラスを使用して表示されます。ARImageNode は画像を使用して初期化されます。画像には、デバイスの OS でサポートされている任意の形式に対応しています。


```java
// Initialise image node
ARImageNode imageNode = new ARImageNode("eyebrow.png");

// Add image node to image trackable
trackable.getWorld().addChild(imageNode);
```

### Video Nodes

ビデオは ARVideoNode クラスを使用して表示されます。ARVideoNode は、iOS 上のビデオファイルと Android 用のビデオファイルから初期化されたビデオテクスチャを使用して初期化されます。 ビデオファイルは、デバイスの OS でサポートされている任意の形式に対応しています。

```java
// Initialise video texture
ARVideoTexture videoTexture = new ARVideoTexture();
videoTexture.loadFromAsset("waves.mp4");

// Initialise video node with video texture
ARVideoNode videoNode = new ARVideoNode(videoTexture);

//Add video node to image trackable
trackable.getWorld().addChild(videoNode);
```

### Alpha Video Nodes

アルファビデオは透明なチャンネルを持つビデオであり、[AR ツールキット](https://www.xlsoft.com/jp/products/kudan/download.html)を使用して透明な PNG とのセットを使って作成することができます。アルファビデオは ARAlphaVideoNode クラスを使用して表示されます。ARAlphaVideoNode はビデオノードと同じように初期化されます。

```java
// Initialise video texture
ARVideoTexture videoTexture = new ARVideoTexture();
videoTexture.loadFromAsset("kaboom.mp4");

// Initialise alpha video node with video texture
ARAlphaVideoNode alphaVideoNode = new ARAlphaVideoNode(videoTexture);

// Add alpha video node to image trackable
trackable.getWorld().addChild(alphaVideoNode);
```

### Model Nodes

モデルは ARModelNode クラスを使用して表示されます。ARModelNode は 2つのステップで作成されます。まずモデルは ARModelImporter クラスを使用してインポートされます。次に、テクスチャーのマテリアルがモデルの個々のメッシュノードに適用されます。マテリアルは、カラーマテリアル、テクスチャーマテリアル、またはライトマテリアルのいずれかです。

KudanAR で 3Dモデルを使用する際の詳細は、以下のページをチェックしてください。

[3D Models](https://www.xlsoft.com/doc/kudan/3d-models/)

Note: ARLightMaterial に照明を追加しないと、マテリアルは黒く表示されます。

```java
// Import model
ARModelImporter modelImporter = new ARModelImporter();
modelImporter.loadFromAsset("ben.jet");
ARModelNode modelNode = (ARModelNode)modelImporter.getNode();

// Load model texture
ARTexture2D texture2D = new ARTexture2D();
texture2D.loadFromAsset("bigBenTexture.png");

// Apply model texture file to model texture material and add ambient lighting
ARLightMaterial material = new ARLightMaterial();
material.setTexture(texture2D);
material.setAmbient(0.8f, 0.8f, 0.8f);

// Apply texture material to models mesh nodes
for (ARMeshNode meshNode : modelImporter.getMeshNodes()){
    meshNode.setMaterial(material);
}

// Add model node to image trackable
trackable.getWorld().addChild(modelNode);
```

### Scaling

マーカーに追加する画像／ビデオ／モデルがマーカーと同じサイズでない場合、ARNode をスケーリングすることができます。ビデオ／画像の提供は trackable と同じアスペクト比であるため、正しいスケールを取得するために幅／高さを分割できます。この値を使用して、ノードをスケーリングできます。

Note: このチュートリアルでは、x、y、z 軸を個別にスケーリングできますが、均一な値を使用してスケーリングします。

```java
// Image scale
ARTextureMaterial textureMaterial = (ARTextureMaterial)imageNode.getMaterial();
float scale = trackable.getWidth() / textureMaterial.getTexture().getWidth();
imageNode.scaleByUniform(scale);

// Video scale
float scale = trackable.getWidth() / videoTexture.getWidth();
videoNode.scaleByUniform(scale);

// Alpha video scale
float scale = trackable.getWidth() / videoTexture.getWidth();
alphaVideoNode.scaleByUniform(scale);
```

### コンテンツの可視性

各ノードにはブール値があり、ノードを表示するかどうかを決定するために設定できます。この設定はマーカーに複数のノードが接続されていて、それらを一度にすべて表示したくない場合に便利です。次を使用して設定できます。

```java
// Hide image node
imageNode.setVisible(false);
```

## 利用方法

- [エクセルソフトの Kudan ダウンロードページ](https://www.xlsoft.com/jp/products/kudan/download.html?utm_source=external&utm_medium=github&utm_campaign=xlsoft_Marker-Basics-Android) から最新の Kudan AR Android SDK のダウンロードをお申し込みください。
- `KudanAR.aar` をプロジェクトフォルダーの `KudanAR` フォルダにコピーまたは移動します。
- ビルドして実行します。

## エクセルソフトについて

エクセルソフトは Kudan AR/CV SDK の販売代理店です。

- AR で出来ることを知りたい
- AR の活用方法を相談したい
- デモ／訪問を希望する
- ライブラリの使い方を知りたい
- 価格を知りたい

などのご相談はお気軽に [お問合せフォーム](https://www.xlsoft.com/jp/services/xlsoft_form.html?option2=Kudan&utm_source=external&utm_medium=github&utm_campaign=xlsoft_Marker-Basics-Android) よりお寄せ下さい。
