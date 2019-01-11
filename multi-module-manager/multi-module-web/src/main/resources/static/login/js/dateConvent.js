function timestampToTime(timestamp) {
    var date = new Date(timestamp * 1000);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
    Y = date.getFullYear() + '-';
    M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
    D = date.getDate() + ' ';
    h = date.getHours() + ':';
    m = date.getMinutes() + ':';
    s = date.getSeconds();
    return Y+M+D+h+m+s;
}

function getLocalTime(nS) {
    return new Date(parseInt(nS) * 1000).toLocaleString().replace(/:\d{1,2}$/,' ');
}

/**
 * 使用十位时间戳时候 easyui用这个转化
 * @param date
 * @returns {string}
 * @constructor
 */
function TimeToDateStamp(date) {
    var da = new Date(date);
    var ts = Math.round(da.getTime()/1000).toString();
    return ts
}

/**
 * 使用dateTime时候easyui用这个转化
 * @param value
 * @returns {string}
 */
function dateTime_to_easyui(value) {
    var unixTimestamp = new Date(value);
    return unixTimestamp.toLocaleString();
}
