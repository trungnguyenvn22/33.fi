import icons_google from "../assets/Google.png";
import Input from "../components/Input";
import Label from "../components/labels/Label";
import LayoutAuthentication from "../layouts/LayoutAuthentication";

const Signup = () => {
  return (
    <LayoutAuthentication>
      <div className="w-[556px] h-[744px] leading-10 bg-white rounded-xl px-16 py-12 mx-auto">
        <h1 className="font-semibold text-xl mb-3 text-black text-center">
          Đăng ký
        </h1>
        <p className="mb-5 text-sm font-medium text-center text-[#808191]">
          ban đã có tài khoản?{" "}
          <strong>
            <a className="text-green-400" href="">
              Đăng nhập
            </a>
          </strong>
        </p>
        <button className="flex items-center justify-center gap-x-3 w-full py-3 border-[#7c7c7d] rounded-xl border-2">
          <img src={icons_google} alt="" />
          <span className="text-base font-semibold text-[#4B5264]">
            Đăng nhập bằng Google
          </span>
        </button>

        <Label>Full name*</Label>

        <Input
          type="text"
          placeholder="ex: Nguyen Hoang Trung"
          name="fullname"
        ></Input>

        <Label>Username</Label>
        <Input
          type="text"
          placeholder="typing your username"
          name="username"
        ></Input>
        <Label>Password</Label>
        <Input
          type="password"
          placeholder="typing your password"
          name="password"
        ></Input>
        <Label>Re-Password</Label>
        <Input
          type="password"
          placeholder="re-typing your password"
          name="re-password"
        ></Input>
      </div>
    </LayoutAuthentication>
  );
};

export default Signup;
