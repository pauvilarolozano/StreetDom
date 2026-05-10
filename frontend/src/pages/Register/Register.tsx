import AuthLayout from "../../auth/components/AuthLayout"
import Button from "../../auth/components/Button"
import FormAuth from "../../auth/components/Form"
import InputField from "../../auth/components/InputField"

const Register = () => {

  return (
    <AuthLayout>
      <FormAuth title="Create Account">
        <InputField label="Username" type="text" placeholder="Enter your username" />
        <InputField label="Password" type="password" placeholder="Enter your password" />
        <InputField label="Confirm Password" type="password" placeholder="Confirm your password" />
        <InputField label="First Name" type="text" placeholder="Enter your first name" />
        <InputField label="Last Name" type="text" placeholder="Enter your last name" />
        <InputField label="Email" type="email" placeholder="Enter your email" />


        <Button name="Create Account" onClick={() => {}} />

      </FormAuth>
    </AuthLayout>
  )
}

export default Register