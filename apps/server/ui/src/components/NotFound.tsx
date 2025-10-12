import { useEffect } from 'react'
import { useNavigate } from '@tanstack/react-router'

export const NotFound = () => {
  const navigate = useNavigate()

  useEffect(() => {
    navigate({ to: '/' })
  }, [navigate])

  return null
}
