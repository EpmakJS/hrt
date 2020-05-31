#!/usr/bin/env python3

import re
import psycopg2
import numpy as np
from psycopg2.extras import DictCursor


def insert_data_from_file_to_database_table(file_path, table_name):
    array = __convert_data_types(__get_array_of_data_from_file(file_path))
    for data in array:
        connection = __get_connection()
        cursor = __get_cursor(connection=connection)
        data_tuple = tuple(data)
        stmt = f"INSERT INTO \"{table_name}\" values {data_tuple}"
        try:
            __execute_statement(stmt, connection, cursor)
        except Exception as e:
            print(f'Error: {e}')
        finally:
            cursor.close()
            connection.close()


def __get_array_of_data_from_file(path):
    row_data_array = []
    with open(path, 'r', encoding='utf-8') as i_f:
        lines = i_f.read().splitlines()
        pattern = re.compile(
            r'^(.{2})(.{1})(.{12})(.{10})(.{10})(.{5})(.{5})(.{10})(.{4})(.{8})(.{15})(.{15})(.{15})'
            r'(.{15})(.{6})(.{12})(.{1})(.{7})(.{7})')
        for line in lines:
            raw_result = pattern.findall(line)
            result = []
            for ind, val in enumerate(raw_result[0]):
                new_val = re.sub(r'\s+', '', val)
                result.append(new_val)
            row_data_array.append(result)
    return row_data_array


def __convert_data_types(arrays):
    data_array = []
    for array in arrays:
        values = []
        arr = np.asarray(array)
        values.append(int(arr[0]))
        values.append(int(arr[1]))
        values.append(float(arr[2]))
        values.append(float(arr[3]))
        values.append(float(arr[4]))
        values.append(float(arr[5]))
        values.append(float(arr[6]))
        values.append(float(arr[7]))
        values.append(float(arr[8]))
        values.append(float(arr[9]))
        values.append(arr[10])
        values.append(arr[11])
        values.append(arr[12])
        values.append(arr[13])
        values.append(int(arr[14]))
        values.append(int(arr[15]))
        values.append(arr[16])
        values.append(float(arr[17]))
        values.append(float(arr[18]))
        data_array.append(values)
    return data_array


def __get_connection():
    connection = psycopg2.connect(
        dbname='hrt',
        user='postgres',
        password='postgres',
        host='localhost',
        port=5432
    )
    connection.autocommit = True
    return connection


def __get_cursor(connection):
    return connection.cursor(cursor_factory=DictCursor)


def __execute_statement(statement, connection, cursor):
    try:
        cursor.execute(statement)
    except Exception as e:
        print(f'Error: {e}')
    else:
        connection.commit()


insert_data_from_file_to_database_table('files/01_hit12.par', '1')

# insert_data_from_file_to_database_table('files/01_hit12.par', '1')
# insert_data_to_database('files/02_hit12.par', '2')
# insert_data_to_database('files/07_hit12.par', '7')
# insert_data_to_database('files/34_hit08.par', '34')
