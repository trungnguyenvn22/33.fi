/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      fontFamily: {
        primary: [
          
        ]
      }, 
      colors: {
        primary: "#1DC071",
        secondary: "#6F49FD", 
        text1: "#171725",
        text2: "#4B5264",
        text3: "#808191",
        text4: "#B2B3BD",
        white: "#FFFFFF"
      }
    },
  },
  plugins: [],
}