<style>
    * {
        margin: 0;
        padding: 0;
    }

    ::-webkit-scrollbar-track {
        background-color: transparent;
    }

    ::-webkit-scrollbar {
        width: 12px;
        height: 12px;
    }

    ::-webkit-scrollbar-thumb {
        background-color: #c0c0c0;
    }

    ::-webkit-scrollbar-thumb:hover {
        background-color: #848484;
    }

    ::-webkit-scrollbar-corner {
        background-color: transparent;
    }

    ::-webkit-scrollbar-button {
        background-color: #848484;
    }

    ::-webkit-scrollbar-button:horizontal:single-button:start {
        border-radius: 12px 0 0 12px;
        cursor: pointer;
        width: 10px;
        height: 15px;
    }

    ::-webkit-scrollbar-button:horizontal:single-button:end {
        border-radius: 0 12px 12px 0;
        cursor: pointer;
        width: 10px;
        height: 15px;
    }

    ::-webkit-scrollbar-button:vertical:single-button:start {
        border-radius: 12px 12px 0 0;
        cursor: pointer;
        width: 15px;
        height: 10px;
    }

    ::-webkit-scrollbar-button:vertical:single-button:end {
        border-radius: 0 0 12px 12px;
        cursor: pointer;
        width: 15px;
        height: 10px;
    }

    a {
        color: #339;
        text-decoration: none;
    }

    a:hover {
        text-decoration: underline;
    }
</style>
<script>
    function fixWidth() {
        return window.parent.client().width - 160;
    }

    function fixHeight() {
        return window.parent.client().height - 84;
    }
</script>

<div style="width: fixWidth();height: fixHeight()" id="show_word">
</div>
<script type="text/javascript">
    document.getElementById("show_word").innerHTML = word;
</script>
