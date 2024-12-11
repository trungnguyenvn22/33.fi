
interface Props {
    type: string;
    placeholder?: string,
    name: string
}

const Input = (props: Props) =>{

    return (
        <input type= {props.type} placeholder={props.placeholder} name={props.name}
        className="px-6 py-4 w-full border-2 border-[#4B5264] rounded-xl text-sm font-medium"
        />
    )
};

export default Input;