
const AuthLayout = ({ children }: { children: React.ReactNode }) => {
  return (
    <div className="bg-gradient-to-br from-green-500 via-green-200 to-green-500 h-screen flex items-center justify-center"> 
      {children}
    </div>
  );
} 

export default AuthLayout;