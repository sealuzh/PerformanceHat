" Script Variables {{{
  let s:hat_command = '-command hat_src_update -p "<project>" -f "<file>"'
" }}}

function! eclim#hat#Hat() " {{{
  if !eclim#project#util#IsCurrentFileInProject(0)
    return
  endif

  let project = eclim#project#util#GetCurrentProjectName()
  let file = eclim#project#util#GetProjectRelativeFilePath()

  let command = s:hat_command
  let command = substitute(command, '<project>', project, '')
  let command = substitute(command, '<file>', file, '')
  " let command = substitute(command, '<offset>', eclim#util#GetOffset(), '')
  " let command = substitute(command, '<encoding>', eclim#util#GetEncoding(), '')

  let result = eclim#Execute(command)
  

	if !eclim#util#WillWrittenBufferClose()
	  if type(result) == g:LIST_TYPE && len(result) > 0
	    let errors = eclim#util#ParseLocationEntries(
	      \ result, g:EclimValidateSortResults)
	    " get old LocList and Add  
	    for err in getloclist(0)
			call add(errors,err)
		endfor
	    call eclim#util#SetLocationList(errors)
	  endif
	endif
	
	call eclim#project#problems#ProblemsUpdate('save')

endfunction " }}}