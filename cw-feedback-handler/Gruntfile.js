// @see: http://ericnish.io/blog/compile-less-files-with-grunt
module.exports = function(grunt) {
  require('jit-grunt')(grunt);

  grunt.initConfig({
    less: {
      development: {
        options: {
          compress: true,
          yuicompress: true,
          optimization: 2
        },
        files: {
            "src/main/webapp/WEB-INF/static/feedbackHandlerApp/app.css": "src/main/webapp/WEB-INF/static/feedbackHandlerApp/app.less"
        }
      }
    },
    watch: {
      styles: {
        files: ['src/main/webapp/WEB-INF/static/feedbackHandlerApp/**/*.less'], // which files to watch
        tasks: ['less'],
        options: {
          nospawn: true
        }
      }
    }
  });

  grunt.registerTask('default', ['less', 'watch']);
  grunt.registerTask('build', ['less']);
};