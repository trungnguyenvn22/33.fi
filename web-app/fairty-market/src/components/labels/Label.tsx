

    interface Props {
        children: string;
        htmlFor?: string
    };
    

    const Label = (props:Props) => {

        return(
            <div>
                <label htmlFor={props.htmlFor} className="text-sm font-medium text-[#4B5264] cursor-pointer ">
                    {props.children}
                </label>
            </div>
        )

    }

    export default Label;