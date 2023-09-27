document.querySelector('form').addEventListener('submit', function(event) {

    // エンドポイントの取得
    let endpoint = event.target.action;

    // サーバーサイドからPDFデータを取得
    fetch(endpoint).then(response => response.json()).then(data => {
        // Base64デコードしてBlobに変換
        let binary = atob(data.pdfData);
        let bytes = new Uint8Array(binary.length);
        for (let i = 0; i < binary.length; i++) {
            bytes[i] = binary.charCodeAt(i);
        }
        let blob = new Blob([bytes], { type: 'application/pdf' });

        // BlobからObject URLを生成
        let url = URL.createObjectURL(blob);

        // ダウンロードリンクを生成してクリック
        let a = document.createElement('a');
        a.href = url;
        a.download = 'filename.pdf';
        a.click();


    // リダイレクト
    window.location.href = contextPath + "/mainMenu/action-success.jsp";
    }).catch(error => {
   		console.error('Fetch error:', error.message || error);
    });
});