const Form = ({title, children}: {title: string; children: React.ReactNode}) => {

  return(
    <form className="bg-white flex flex-col items-center gap-5 rounded-lg p-12 shadow-lg">
      <h1 className="text-3xl font-extrabold text-center text-green-500 ">{title}</h1>
      {children}
    </form>
  )
}

export default Form