% \documentclass{article}
\NeedsTeXFormat{LaTeX2e} 
\ProvidesClass{notes}[2013/09/27 Notes Class] 
\LoadClass{article}
\usepackage{graphicx} % new way of doing eps files
\usepackage{listings} % nice code layout
\usepackage[usenames]{color} % color
\usepackage{amsmath}
\usepackage{placeins} % mainly for \FloatBarrier
\usepackage{indentfirst} % Make the first line of all sections etc., be indented by the usual paragraph indentation.
\usepackage{mathtools} % for rcases*, dcases etc
\usepackage[normalem]{ulem} % for underlining text, e.g. \uline{...}, \uwave{}, \sout{}, \xout{}, etc.
\usepackage{marginnote} % for margin notes
\usepackage{amssymb}    % for \mathbb
\usepackage{mathrsfs}   % for fonts \mathcal{}, \mathscr{}, etc
\definecolor{listinggray}{gray}{0.9}
\definecolor{graphgray}{gray}{0.7}
\definecolor{blue}{rgb}{0,0,1}
% \MatLab{title}{label}{file}
\newcommand{\eqnote}[1]{\mbox{\hspace{10pt}#1\hspace{10pt}}}

\newcommand{\MatLab}[3]{
  \lstset{language=Matlab}
  \lstset{backgroundcolor=\color{listinggray},rulecolor=\color{blue}}
  \lstset{linewidth=\textwidth}
  \lstset{commentstyle=\textit, stringstyle=\upshape,showspaces=false}
  \lstset{frame=tb}
  \lstinputlisting[caption={#1},label={#2}]{#3}
}
% \SciLab{title}{label}{file}
\newcommand{\SciLab}[3]{
  \lstset{language=Scilab}
  \lstset{backgroundcolor=\color{listinggray},rulecolor=\color{blue}}
  \lstset{linewidth=\textwidth}
  \lstset{commentstyle=\textit, stringstyle=\upshape,showspaces=false}
  \lstset{frame=tb}
  \lstinputlisting[caption={#1},label={#2}]{#3}
}
% \Ccode{title}{label}{file}
\newcommand{\Ccode}[3]{
  \lstset{language=c}
  \lstset{backgroundcolor=\color{listinggray},rulecolor=\color{blue}}
  \lstset{linewidth=\textwidth}
  \lstset{commentstyle=\textit, stringstyle=\upshape,showspaces=false}
  \lstset{frame=tb}
  \lstinputlisting[caption={#1},label={#2}]{#3}
}

\newcommand{\Java}[3]{
  \lstset{language=Java}
  % \lstset{backgroundcolor=\color{listinggray},rulecolor=\color{blue}}
  \lstset{linewidth=\textwidth}
  \lstset{commentstyle=\textit, stringstyle=\upshape,showspaces=false}
  \lstset{frame=tb}
  \lstinputlisting[caption={#1},label={#2}]{#3}
}
