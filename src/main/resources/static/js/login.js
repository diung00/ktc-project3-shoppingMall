
if (localStorage.getItem("jwt")){       //ktra xem token có trong localstorage hay không
                                        //nếu user đã login thì token đc lưu ở đây
    location.href = "/views/user-page";  //nếu đã login, user sẽ đc điều hướng tới trang này.
}

const loginForm = document.getElementById("login-form");            //lấy đối tượng bằng Id, user nhập thông tin từ form này
const usernameInput  = document.getElementById("username");   //nơi nhập username và password
const passwordInput = document.getElementById("password");

loginForm.addEventListener("submit", (e) =>{            //user ấn submit thì
    e.preventDefault();
    const username = usernameInput.value;               // lấy giá trị
    const password = passwordInput.value;               // lấy giá trị

    fetch("/token/issue", {                             // dùng fetch để gửi yêu cầu http, đây là yêu cầu login để nhận token
        method: "post",                                 // gửi data
        headers: {
            "Content-Type": "application/json",         // báo dữ liệu gửi đi ở dạng json
        },
        body: JSON.stringify({                          // chuyển đổi đối tượng chứ username và password thành chuỗi json
            username,                                   // và gửi trong phần thân của yêu cầu
            password,
        }),
    }).then(response => {                               // khi server gửi phản hồi, đoạn code sau sẽ xử lí
        if (response.ok){                               // xem phải hồi oke không (status code từ 200~299)
            return response.json();                     //nếu ok, phân tích phản hồi json từ server
        }
        else throw Error(response.statusText);          // nếu k ok, ném ra mã, ví dụ 400 hoặc 500
    })

    .then(json => {                                     //sau khi phân tích thành công json, mã này xử lý data đã trả về
        console.log(json);                              // in đối tượng json
        console.log(json.token);                        // in token
        localStorage.setItem("jwt", json.token);        // lưu token vào localstorage, dùng cho các yêu cầu tiếp theo mà không cần đăng nhập lại
        location.href = "/views/user-page"               // hướng user tới sang này sau khi login oke
    })
    .catch(e=> alert(e.message));                       // bắt tất cả các lỗi và hiện thị thông báo lỗi qua alert
});









