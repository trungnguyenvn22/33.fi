import { lazy } from "react";
import { Route, Routes } from "react-router-dom";
import './App.css';
import Category from "./components/category/Category.tsx";
import LayoutAuthentication from "./layouts/LayoutAuthentication.tsx";
import ForgotPassPage from "./pages/ForgotPassPage.tsx";

  const Signup = lazy(()=> import("./pages/Signup.tsx"));

function App() {
  


  return (
    <Routes>
      <Route path="/sign-up" element = {<Signup></Signup>}></Route>
      <Route path="/layout-auth" element = {<LayoutAuthentication></LayoutAuthentication>}></Route>
      <Route path="/category" element= {<Category ></Category>}></Route>
      <Route path="/forgot-password" element = {<ForgotPassPage></ForgotPassPage>}></Route>
    </Routes>
  )
}

export default App
