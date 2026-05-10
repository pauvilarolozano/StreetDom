import { useNavigate } from "react-router-dom"
import AuthLayout from "../../auth/components/AuthLayout";
import Form from "../../auth/components/Form";
import InputField from "../../auth/components/InputField";
import Button from "../../auth/components/Button";

const Login = () => {

  const navigate = useNavigate();

  const handleCreateAccount = () => {
    navigate("/register");
  }

  return (
    <AuthLayout>
      <Form title="Log In">
        <InputField label="Username" type="text" placeholder="Enter your username" />
        <InputField label="Password" type="password" placeholder="Enter your password" />

        <Button name="Log In" onClick={() => {}} />

        <div className="w-full">
          <p className="text-sm text-gray-600"> Don't have an account? </p>
          <Button name="Create Account" onClick={handleCreateAccount} />
        </div>
      </Form>
    </AuthLayout>
  )
}

export default Login
