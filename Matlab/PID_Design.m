%% PID Controller Design

% Define the plant and its parameters
M = 0.623;          % Mass of pendulum in kg
m = 0.035;          % Mass of wheels in kg
L = 0.2075;         % Length of pendulum in meters
r = 0.021;          % Radius of the wheel in meters
g = 9.8;            % Gravitational force = 9.8 m/sec^2

Pend = tf([2*L*r*(M+m)],[m*L^2 0 -4*m*g])

% Define the PID Controller
Kp = 1;
Ki = 1;
Kd = 1;
C = pid(Kp,Ki,Kd);      % The PID controller

T = feedback(Pend,C); % The closed loop transfer function for the entire system

%% Simulate the Impulse Response

t= 0:0.01:10;
impulse(T,t)
title('Response of Pendulum Position to an Impulse Disturbance under PID Control: Kp = 1, Ki = 1, Kd = 1')

%% Try with Kp = 300 and Kd = 20
Kp = 300;
Kd = 20;
C = pid(Kp,Ki,Kd);     

T = feedback(Pend,C)
impulse(T,t)
title('Response of Pendulum Position to an Impulse Disturbance under PID Control: Kp = 300, Ki = 1, Kd = 20')

%% Find poles and zeros of system

[num den] = tfdata(T,'v');
[z p k] = tf2zp([[num]], [[den]])

figure(2) 
rlocus(T)

figure(3)
bode(T)
