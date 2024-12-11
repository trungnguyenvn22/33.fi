import folder_icon from "./folder_black_24dp 2.png";

interface Category {
    folder_name: string
    title: string
    description: string
    total_raise: number
    total_backer: number
    currentRaise: number
}

 const Category = () =>{


    return (
        <div className="w-[288px] h-[420px] rounded-2xl bg-slate-100">
            <img src="https://images.unsplash.com/photo-1731902062588-4dce45ccc0cb?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D" alt=""
                 className="w-full h-[180px] rounded-2xl"
            />
            <div className="p-2">
               
                    <div className="flex px-2">
                    <img src={folder_icon} alt=""
                    className="w-5 h-5 object-cover"
                    />
                    <p className="pl-3 font-medium text-sm text-[#808191] inline-block"> Education</p>
                    </div>
                    <h1 className="text-lg font-semibold text-black ">Powered Kits Learning Boxes</h1>
                    <p className="text-base font-medium text-[#808191]">Fun, durable and reusable boxes with
                    eco-friendly options.</p>
                    <div className="pt-5 flex justify-between">
                    <div className="">
                        <h1 className=" font-semibold text-sm text-black">$2000</h1>
                        <p className="text-sm font-medium text-[#808191]">Raised of $1500</p>
                    </div>
                    <div className="">
                        <h1 className=" font-semibold text-sm text-black">200</h1>
                        <p className="text-sm font-medium text-[#808191]">Total backers</p>
                    </div>
                    </div>

                    <div className="flex pt-5 align-middle">
                        <img className="w-8 h-8 rounded-full"
                        src="https://images.unsplash.com/photo-1489755730820-cd90f9a49424?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D" alt="" />
                        <h1 className="pl-2 text-base font-semibold text-black">Nguyễn Hoàng Trung</h1>
                    </div>

                    

                <div>

                </div>
            </div>

        
        </div>
    )
 }
 
 export default Category;