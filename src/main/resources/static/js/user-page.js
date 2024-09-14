const jwt = localStorage.getItem("jwt");                       //lấy token từ localstorage, nếu user đã đăng nhập thì token đã đc lưu ở đây
if(!jwt){                                                      // ktra xem có token không, nếu không tìm được, thì chưa login hoặc token đã bị xóa, điều kiên trả về true
    location.href = "/views/login";                            // chưa có token thì user cần login
}

function addUser(username, name, nickname, age, phone, email, password){
       const userDiv = document.createElement("div");
       userDiv.innerHTML = `
       <h3>${username}</h3>
       <p>${name}</p>
       <p>${nickname}</p>
       <p>${age}</p>
       <p>${phone}</p>
       <p>${email}</p>
       <p>${password}</p>
       `;
       document.getElementById("user-page").appendChild(userDiv);
}
fetch("/user-page",{
    method: "get",
    headers: {
        "Authorization": `Bearer ${jwt}`
    },
})
.then(response => {
    if(response.ok){
        return response.json();
    }
    else if (response.status === 403){                          //nếu server trả về mã trạng thái 403 thì token đã hết hạn hoặc không hợp lệ
        localStorage.removeItem("jwt");                           //xóa token không hợp lệ để tránh sử dụng trong tương lai
        location.href = "/views/login";                           //rồi điều hướng tới trang login, để nhận được token mới
      }
      else throw Error("failed to fetch");

})
.then(json => {
    console.log(json);
  })
  .catch(e => {
    console.error(e);
  });



