const Login = () => {
  
  return (
    <>
      <div className="bg-gradient-to-br from-green-500 via-green-200 to-green-500 h-screen flex items-center justify-center"> 
        <form className="bg-white flex flex-col items-center gap-5 rounded-lg p-12 shadow-lg">
          <h1 className="text-3xl font-extrabold text-center text-green-500 ">Log In</h1>

            <div className="flex flex-col gap-2">
              <label className="block text-left text-sm text-gray-700 font-bold">Username</label>
              <input className="border p-2 rounded w-full" type="text" placeholder='Enter your username'  />
            </div>

            <div className="flex flex-col gap-2">
              <label className="block text-left text-sm text-gray-700 font-bold">Password</label>
              <input className="border p-2 rounded w-full" type="password" placeholder='Enter your password' />
            </div>
            <button className="bg-blue-500 text-white p-2 rounded-lg w-full" type='submit'>Log In</button>

            <div className="w-full">
              <p className="text-sm text-gray-600"> Don't have an account? </p>
              <button className="bg-blue-500 text-white p-2 rounded-lg w-full" type='submit'>Create Account</button>
            </div>
        </form>
      </div>

    </>
  )
}

export default Login
