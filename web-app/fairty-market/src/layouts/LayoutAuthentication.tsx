import React from "react";
import Logo from "../assets/Logo.png";


interface Props {
    children?: React.ReactNode
    class?: string
    
}


 const LayoutAuthentication = (props : Props) =>{

    const {children} = props


    return (
        <div className= {`w-[1440px] h-[1080px] bg-slate-400 p-10 ${props.class}`} >

            <div className="w-[52px] h-[52px]">
               <img src= {Logo} alt="logo" 
                    className="object-cover inline-block"
               /> 
            </div>

            {children}

        </div>
    )
 };

 export default LayoutAuthentication;