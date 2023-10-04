document.addEventListener("DOMContentLoaded", function () {
    // Get the data from the div's data- attributes
    let pdfDataContainer = document.getElementById("pdfDataContainer");
    let base64PdfData = pdfDataContainer.getAttribute("data-base64pdf");
    let pdfFilename = pdfDataContainer.getAttribute("data-pdffilename");

    // Create an anchor element
    let a = document.createElement('a');
    a.href = "data:application/pdf;base64," + base64PdfData;
    a.download = pdfFilename;

    // Trigger the download by simulating a click on the anchor element
    a.click();
});
