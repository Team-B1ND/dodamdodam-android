import os
import fnmatch

def calculate_stability_percentage(file_path):
    stable_count = 0
    unstable_count = 0

    with open(file_path, 'r') as file:
        class_info = file.readlines()

    for line in class_info:
        if 'stable class' in line:
            stable_count += 1
        if 'unstable class' in line:
            unstable_count += 1
        if 'stable val' in line:
            stable_count += 1
        if 'unstable val' in line:
            unstable_count += 1

    total_count = stable_count + unstable_count

    if total_count == 0:
        return 0, 0  # Avoid division by zero

    stable_percentage = (stable_count / total_count) * 100
    unstable_percentage = (unstable_count / total_count) * 100

    return stable_percentage, unstable_percentage

def find_files(start_directory, pattern):
    matches = []
    for root, dirs, files in os.walk(start_directory):
        for filename in fnmatch.filter(files, pattern):
            matches.append(os.path.join(root, filename))
    return matches


# 사용 예
if __name__ == "__main__":
    # 현재 디렉토리 또는 원하는 시작 디렉토리 설정
    start_directory = '.'
    # 파일 패턴 정의
    pattern = '*_release-classes.txt'

    # 파일 찾기
    found_files = find_files(start_directory, pattern)

    # 결과 출력
    total_stable_percentage = 0
    total_unstable_percentage = 0

    for file_path in found_files:
        print(file_path)
        stable_percentage, unstable_percentage = calculate_stability_percentage(file_path)
#         print(f"Stable Percentage: {stable_percentage:.2f}%")
#         print(f"Unstable Percentage: {unstable_percentage:.2f}%")
        total_stable_percentage += stable_percentage
        total_unstable_percentage += unstable_percentage
    print(f"Stable Result : {(total_stable_percentage / len(found_files)):.2f}%")
    print(f"Unstable result : {(total_unstable_percentage / len(found_files)):.2f}%")