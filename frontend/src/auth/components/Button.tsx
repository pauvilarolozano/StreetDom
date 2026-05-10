
const Button = ({name, onClick} : {name: string, onClick: () => void}) => {

  return(
    <button className="bg-blue-500 text-white p-2 rounded-lg w-full" type='submit' onClick={onClick}>
      {name}
    </button>
  )
}

export default Button