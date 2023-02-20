<h2>7Tour旅遊網</h2>

<h3>簡介</h3>
<p>供使用者訂房、行程規劃、旅遊揪團、論壇討論的平台</p>
:movie_camera:<a href="https://youtu.be/Tl_DXhWUFOk?t=683">影片介紹</a>
:scroll:<a href="https://drive.google.com/file/d/1XtUt0F1rDTHjMEt67zOaiTftHDCd31QN/view?usp=sharing">文件介紹</a>

<h3>技術</h3>
<ul>
<li>程式語言 ： Java17 + JavaScript</li>
<li>資料庫 ： MySQL 8.0 + Redis 3.2</li>
<li>MVC架構 ： JDBC + JSP + Servlet 4 + Tomcat 9</li>
<li>補充 ： JQuery、Ajax、WebSocket、Ecpay、Bootstrap5</li>
<li>IDE：Eclipse 2022-03 (4.23)</li>
</ul>

<h3>用法</h3>
<ol>
        <li>Use MySQL to create a database "cga105_g7" and import <a href="https://github.com/wanying0326/CGA105G7-7Tour/tree/master/CGA105G7/src/main/java/createDB">sql file</a>.</li> 
        <li>Set MySQL user name = "root" and password = "02021"</li>
        <li>Start the server and navigate to <a href="http://localhost:8080/CGA105G7/Room?action=toRoomIndex">:globe_with_meridians:http://localhost:8080/CGA105G7/Room?action=toRoomIndex</a></li>
</ol>

<h3>負責項目：訂房、即時客服聊天室<a href="https://drive.google.com/file/d/1pe-7fkhkZp1bEPswPSmy2oXqYVYQAgD8/view?usp=sharing">:pushpin:</a></h3>

<div><b>＃前台</b></div>
<ul>
        <li>搜尋及瀏覽旅宿店家及房型 -> 
        <a href="https://github.com/wanying0326/CGA105G7-7Tour/tree/master/CGA105G7/src/main/java/com/vendor">java/com/vendor</a> 
        & <a href="https://github.com/wanying0326/CGA105G7-7Tour/tree/master/CGA105G7/src/main/java/com/room">java/com/room</a></li>
        <li>會員收藏店家 -> <a href="https://github.com/wanying0326/CGA105G7-7Tour/tree/master/CGA105G7/src/main/java/com/ColVen">java/com/ColVen</a></li>
        <li>使用購物車與會員結帳 -> <a href="https://github.com/wanying0326/CGA105G7-7Tour/tree/master/CGA105G7/src/main/java/com/shoppingCart">java/com/shoppingCart</a>
        & <a href="https://github.com/wanying0326/CGA105G7-7Tour/tree/master/CGA105G7/src/main/java/com/ecpayPayment/controller">java/com/ecpayPayment</a></li>
        <li>會員訂單管理及評價 -> <a href="https://github.com/wanying0326/CGA105G7-7Tour/tree/master/CGA105G7/src/main/java/com/roomOrder">java/com/roomOrder</a></li>
        <li>即時客服聊天室 -> <a href="https://github.com/wanying0326/CGA105G7-7Tour/tree/master/CGA105G7/src/main/java/com/customerService">java/com/customerService</a></li>
</ul>

<div><b>＃後台</b></div>
<ul>
        <li>房型上架 -> <a href="https://github.com/wanying0326/CGA105G7-7Tour/tree/master/CGA105G7/src/main/java/com/room">java/com/room</a></li>
        <li>廠商房務及訂單管理 ->  <a href="https://github.com/wanying0326/CGA105G7-7Tour/tree/master/CGA105G7/src/main/java/com/roomOrder">java/com/roomOrder</a></li>
        <li>廠商空房查詢 -> <a href="https://github.com/wanying0326/CGA105G7-7Tour/tree/master/CGA105G7/src/main/java/com/stock">java/com/stock</a></li>
        <li>客服聊天室管理 ->  <a href="https://github.com/wanying0326/CGA105G7-7Tour/tree/master/CGA105G7/src/main/java/com/customerService">java/com/customerService</a></li>
</ul>

<h3>頁面展示</h3>
<table>
        <tr>
                <th>前台-訂房首頁</th>
                <th>前台-旅宿店家瀏覽</th>
                <th>前台-客服聊天室</th>
                <th>後台-客服管理</th>
                <th>後台-廠商房務管理</th>
        </tr>
        <tr>
                <td><img src="https://user-images.githubusercontent.com/121594182/220048846-1464d6a6-c78d-4080-ad84-165ec7928772.png" alt= “RoomFrontIndex” width="200"></td>
                <td><img src="https://user-images.githubusercontent.com/121594182/220054790-8f96e706-13ef-48ff-a095-1ff5f679df9d.png" alt= “BrowseVendorRoom” width="200"></td>
                <td><img src="https://user-images.githubusercontent.com/121594182/220055656-6e0a4e47-7932-4230-88ff-bcba3fecdb7c.JPG" alt= “CustomerServiceChatRoom” width="200"></td>
                 <td><img src="https://user-images.githubusercontent.com/121594182/220074025-8dca8540-a648-4eea-b557-4d62bcd9966b.JPG" alt= “UserOrderManagement” width="200"></td>
                  <td><img src="https://user-images.githubusercontent.com/121594182/220074283-2b7ea5ee-a3b3-482c-a105-5e9542b99c30.png" alt= “UserOrderManagement” width="200"></td>
        </tr>
</table>

