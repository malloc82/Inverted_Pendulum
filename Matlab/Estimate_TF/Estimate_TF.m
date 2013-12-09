%% Try to use tfestimate to find the transfer function of our system

data00; %Load the data into the workspace
tfestimate(acc,angle) % plot transfer function
NFFT = 2*(length(txy)+1)
[txy,w] = tfestimate(acc,angle,[],NFFT)
%[b,a]=invfreqz(txy,w,30,0)
%[NUM,DEN]= zp2tf(b,a)
