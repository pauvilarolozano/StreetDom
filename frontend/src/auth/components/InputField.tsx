
const InputField = ({label, type, placeholder}: {label: string; type: string; placeholder: string}) => {

  return(
    <div className="flex flex-col gap-2">
      <label className="block text-left text-sm text-gray-700 font-bold">{label}</label>
      <input className="border p-2 rounded w-full" type={type} placeholder={placeholder} />
    </div>
  )
}

export default InputField