const conditionals = process.env.NODE_ENV === 'production'
    ? [require('cssnano')({preset: 'default',})]
    : []

module.exports = {
  plugins: [
    require('tailwindcss')('./tailwind.config.js'), ...conditionals,
    // require('autoprefixer'),
  ],
};
