let token = null;

export function setToken(newToken) {
  token = newToken;
}

export function getToken() {
  return token;
}

export function clearToken() {
  token = null;
}

export function isAuthenticated() {
  return !!token;
}

