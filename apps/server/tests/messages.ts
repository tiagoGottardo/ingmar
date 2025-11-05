export interface IUsuarioPayload {
  nome?: string
  senha?: string
}

export interface IFilmePayload {
  id?: string
  titulo?: string
  diretor?: string
  ano?: string
  genero?: string[]
  sinopse?: string
}

export interface IReviewPayload {
  id?: string
  id_filme?: string
  titulo?: string
  descricao?: string
  nota?: string
}

// LOGIN 
export const login = (usuario: string, senha: string) => {
  return {
    operacao: "LOGIN",
    usuario,
    senha
  }
}

// ADMIN_EXCLUIR_USUARIO
export const adminDeleteUser = (token: string, id: string) => {
  return {
    operacao: "ADMIN_EXCLUIR_USUARIO",
    token,
    id
  }
}

// ADMIN_EDITAR_USUARIO
export const adminEditUser = (token: string, id: string, usuario: IUsuarioPayload) => {
  return {
    operacao: "ADMIN_EDITAR_USUARIO",
    usuario,
    token,
    id
  }
}

// CRIAR_FILME
export const createMovie = (token: string, filme: IFilmePayload) => {
  return {
    operacao: "CRIAR_FILME",
    filme,
    token
  }
}

// CRIAR_REVIEW
export const createReview = (token: string, review: IReviewPayload) => {
  return {
    operacao: "CRIAR_REVIEW",
    review,
    token
  }
}

// EXCLUIR_FILME
export const deleteMovie = (token: string, id: string) => {
  return {
    operacao: "EXCLUIR_FILME",
    id,
    token
  }
}

// EXCLUIR_REVIEW
export const deleteReview = (token: string, id: string) => {
  return {
    operacao: "EXCLUIR_REVIEW",
    id,
    token
  }
}

// BUSCAR_FILME_ID
export const getMovieById = (token: string, id_filme: string) => {
  return {
    operacao: "BUSCAR_FILME_ID",
    id_filme,
    token
  }
}

// LISTAR_FILMES
export const listMovies = (token: string) => {
  return {
    operacao: "LISTAR_FILMES",
    token
  }
}

// LISTAR_REVIEWS_USUARIO
export const listUserReviews = (token: string) => {
  return {
    operacao: "LISTAR_REVIEWS_USUARIO",
    token
  }
}

// LISTAR_USUARIOS
export const listUsers = (token: string) => {
  return {
    operacao: "LISTAR_USUARIOS",
    token
  }
}

// LOGOUT
export const logout = (token: string) => {
  return {
    operacao: "LOGOUT",
    token
  }
}

// LISTAR_PROPRIO_USUARIO
export const listOwnUser = (token: string) => {
  return {
    operacao: "LISTAR_PROPRIO_USUARIO",
    token
  }
}

// CRIAR_USUARIO
export const createUser = (usuario: IUsuarioPayload) => {
  return {
    operacao: "CRIAR_USUARIO",
    usuario
  }
}

// EXCLUIR_PROPRIO_USUARIO
export const deleteOwnUser = (token: string) => {
  return {
    operacao: "EXCLUIR_PROPRIO_USUARIO",
    token
  }
}

// EDITAR_PROPRIO_USUARIO
export const editOwnUser = (token: string, usuario: IUsuarioPayload) => {
  return {
    operacao: "EDITAR_PROPRIO_USUARIO",
    usuario,
    token
  }
}

// EDITAR_FILME
export const editMovie = (token: string, filme: IFilmePayload) => {
  return {
    operacao: "EDITAR_FILME",
    filme,
    token
  }
}

// EDITAR_REVIEW
export const editReview = (token: string, review: IReviewPayload) => {
  return {
    operacao: "EDITAR_REVIEW",
    review,
    token
  }
}
