import subprocess
import heapq

def getProcess():
	res_map = {}
	heap = []

	file = open("output.txt", "w")
	file.write("PID executingTime CPU Memory\n")

	pl = subprocess.Popen(['ps', 'U', '0'], stdout=subprocess.PIPE).communicate()[0]
	processes = pl.split('\n')
	if len(processes) <= 1:
		return
	processes = processes[1:]
	for process in processes:
		process_info = process.split()
		if len(process_info) != 5:
			continue

		pid, execTime = process_info[0], process_info[3]
		heapq.heappush(heap, (execTime, pid))
		if len(heap) > 3:
			heapq.heappop(heap)
	print heap
	for time, pid in heap:
		pl2 = subprocess.Popen(['ps', '-p', pid, '-o %cpu, %mem'], stdout=subprocess.PIPE).communicate()[0]
		infos = pl2.split('\n')
		if len(infos) < 2:
			continue
		temp = infos[1].split()
		if len(temp) < 2:
			continue
		
		cpu_info, mem_info = temp[0], temp[1]
		file.write("%s %s %s %s\n" %(pid, time, cpu_info, mem_info))

	file.close()


if __name__ == '__main__':
	getProcess()
