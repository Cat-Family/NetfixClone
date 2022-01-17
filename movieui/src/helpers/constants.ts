interface router {
  startNow: string;
  signIn: string;
  signUp: string;
  recoverPassword: string;
  recoverPasswordSuccess: string;
  recoverPasswordCode: string;
  home: string;
  tvShows: string;
  movies: string;
  popular: string;
  myList: string;
  search: string;
}

export const routes: router = {
  startNow: "/",
  signIn: "/signIn",
  signUp: "/signUp",
  recoverPassword: "/recover-password",
  recoverPasswordSuccess: "/recover-password/success",
  recoverPasswordCode: "/recover-password/:code",

  home: "/home",
  tvShows: "/tv-shows",
  movies: "/movies",
  popular: "/popular",
  myList: "/my-list",
  search: "/search",
};

export const actions = {
  signUp: "signUp",
  signIn: "signIn",
  signInEmail: "signInEmail",
  autoSignIn: "autoSignIn",
  signInGoogle: "signInGoogle",
  signInFacebook: "signInFacebook",
  signInAnonymously: "signInAnonymously",
  recoverPassword: "recoverPassword",
  recoverPasswordWithEmail: "recoverPasswordWithEmail",
  logout: "logout",
  setUser: "setUser",
  setLoading: "setLoading",
  setGenres: "setGenres",
  setConfiguration: "setConfiguration",
  setError: "setError",
  clearError: "clearError",
  setMyList: "setMyList",
  addMovieToMyList: "addMovieToMyList",
  removeMovieFromMyList: "removeMovieFromMyList",
};
