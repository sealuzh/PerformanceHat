command EclimHat :call eclim#hat#Hat()

if &ft == 'java'
  augroup eclim_java
    autocmd! BufWritePost <buffer>
    autocmd BufWritePost <buffer> call eclim#lang#UpdateSrcFile('java')
    autocmd BufWritePost <buffer> call eclim#hat#Hat()
  augroup END
endif


