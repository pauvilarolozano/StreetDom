import AppRouter from './routes/AppRouter'
import { AuthProvider } from './auth/context/AuthProvider'

const App = () => {
  return (
    <AuthProvider>
      <AppRouter />
    </AuthProvider>
  )
}

export default App
 