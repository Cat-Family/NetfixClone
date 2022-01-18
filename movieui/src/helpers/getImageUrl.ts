import store from "../store";

const config = {
  images: {
    base_url: "http://image.tmdb.org/t/p/",
    backdrop_sizes: ["w300", "w780", "w1280", "original"],
    poster_sizes: ["w92", "w154", "w185", "w342", "w500", "w780", "original"],
  },
};

function getImageUrl(url, size = 3, type) {
  if (type === "backdrop") {
    return config.images.base_url + config.images.backdrop_sizes[size] + url;
  } else {
    return config.images.base_url + config.images.poster_sizes[size] + url;
  }
}

export default getImageUrl;
