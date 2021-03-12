const colors = require('tailwindcss/colors')

module.exports = {
  purge: [],
  darkMode: false, // or 'media' or 'class'
  theme: {
      colors: {
        bgray: colors.blueGray,
        cgray: colors.coolGray,
        gray: colors.gray,
        blue: colors.blue
      }
     },
variants: {},
plugins: []
}
