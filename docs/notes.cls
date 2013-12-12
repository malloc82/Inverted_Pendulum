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
\usepackage[makeroom]{cancel} % used for generate "cross out" effect


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
  \lstset{backgroundcolor=\color{listinggray},rulecolor=\color{blue}}
  \lstset{linewidth=\textwidth}
  \lstset{commentstyle=\textit, stringstyle=\upshape,showspaces=false}
  \lstset{frame=tb}
  \lstinputlisting[caption={#1},label={#2}]{#3}
}

% source: http://stackoverflow.com/questions/741985/latex-source-code-listing-like-in-professional-books
% \SourceCode{language}{caption}{label}{file_path}
\newcommand{\SourceCode}[4]{
  % #1 = language, usually first letter is in upper case
  % #2 = caption 
  % #3 = label
  % #4 = path
  \lstset{language=#1}
  \lstset{
    commentstyle=\color{orange}\textit,
    basicstyle=\footnotesize\ttfamily, % Standardschrift
    % numbers=left,               % Ort der Zeilennummern
    numberstyle=\tiny,          % Stil der Zeilennummern
    % stepnumber=2,               % Abstand zwischen den Zeilennummern
    numbersep=5pt,              % Abstand der Nummern zum Text
    tabsize=2,                  % Groesse von Tabs
    extendedchars=true,         %
    breaklines=true,            % Zeilen werden Umgebrochen
    keywordstyle=\color{red},
    frame=b,         
    % keywordstyle=[1]\textbf,    % Stil der Keywords
    % keywordstyle=[2]\textbf,    %
    % keywordstyle=[3]\textbf,    %
    % keywordstyle=[4]\textbf,   \sqrt{\sqrt{}} %
    stringstyle=\color{blue}\ttfamily, % Farbe der String
    showspaces=false,           % Leerzeichen anzeigen ?
    showtabs=false,             % Tabs anzeigen ?
    xleftmargin=17pt,
    framexleftmargin=17pt,
    framexrightmargin=5pt,
    framexbottommargin=4pt,
    backgroundcolor=\color{listinggray},
    % backgroundcolor=\color{lightgray},
    showstringspaces=false      % Leerzeichen in Strings anzeigen ?        
  }  
  \lstinputlisting[caption={#2},label={#3}]{#4}
}
